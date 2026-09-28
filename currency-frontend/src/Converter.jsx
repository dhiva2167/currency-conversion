import { useState, useEffect } from 'react';
import { CURRENCIES, getCurrencyInfo } from './currencies';

const API_BASE = import.meta.env.VITE_API_URL || '';
const QUICK_AMOUNTS = [50, 100, 250, 500, 1000, 5000];

function Converter() {
  const [theme, setTheme] = useState('light');
  const [amount, setAmount] = useState('100');
  const [from, setFrom] = useState('USD');
  const [to, setTo] = useState('EUR');
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [copied, setCopied] = useState(false);
  const [isSwapping, setIsSwapping] = useState(false);

  // Conversion History
  const [history, setHistory] = useState([]);
  const [historyLoading, setHistoryLoading] = useState(false);

  // Supported Currencies list
  const [currencyOptions, setCurrencyOptions] = useState(CURRENCIES);

  // Fetch supported currencies & history on mount
  useEffect(() => {
    fetchSupportedCurrencies();
    fetchHistory();
  }, []);

  const fetchSupportedCurrencies = async () => {
    try {
      const res = await fetch(`${API_BASE}/api/currencies`);
      if (res.ok) {
        const codes = await res.json();
        if (Array.isArray(codes) && codes.length > 0) {
          const mapped = codes.map((c) => getCurrencyInfo(c));
          mapped.sort((a, b) => a.name.localeCompare(b.name));
          setCurrencyOptions(mapped);
        }
      }
    } catch {
      // Fallback to local currency list if API is warming up
    }
  };

  const fetchHistory = async () => {
    setHistoryLoading(true);
    try {
      const res = await fetch(`${API_BASE}/api/history`);
      if (res.ok) {
        const data = await res.json();
        if (Array.isArray(data)) {
          setHistory(data);
        }
      }
    } catch {
      // Silent catch on init
    } finally {
      setHistoryLoading(false);
    }
  };

  const handleConvert = async (e) => {
    if (e) e.preventDefault();
    if (!amount || parseFloat(amount) <= 0) {
      setError('Please enter a valid amount.');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const response = await fetch(`${API_BASE}/api/convert`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          from,
          to,
          amount: parseFloat(amount),
        }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Conversion failed. Please try again.');
      }

      const data = await response.json();
      setResult(data);
      fetchHistory();
    } catch (err) {
      setError(err.message || 'Unable to connect to the server.');
    } finally {
      setLoading(false);
    }
  };

  const handleSwap = () => {
    setIsSwapping(true);
    const temp = from;
    setFrom(to);
    setTo(temp);
    setTimeout(() => setIsSwapping(false), 300);

    if (result) {
      setResult(null);
    }
  };

  const handleCopyResult = () => {
    if (!result) return;
    const text = `${result.originAmount} ${result.from} = ${result.convertedAmount.toFixed(4)} ${result.to}`;
    navigator.clipboard.writeText(text);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const fromInfo = getCurrencyInfo(from);
  const toInfo = getCurrencyInfo(to);

  return (
    <div className={`page-wrapper ${theme === 'dark' ? 'theme-dark' : 'theme-light'}`}>
      <div className="app-container">
        {/* Header bar */}
        <header className="app-header">
          <div className="brand">
            <span className="brand-logo">💱</span>
            <div>
              <h1 className="brand-name">Currency Converter</h1>
              <p className="brand-tagline">Real-time exchange rates & history</p>
            </div>
          </div>
          <button
            type="button"
            className="theme-toggle"
            onClick={() => setTheme(theme === 'light' ? 'dark' : 'light')}
            title="Toggle theme"
          >
            {theme === 'light' ? '🌙 Dark Mode' : '☀️ Light Mode'}
          </button>
        </header>

        {/* Main Card */}
        <main className="converter-card">
          <form onSubmit={handleConvert} className="converter-form" id="conversion-form">
            {/* Amount Section */}
            <div className="field-group">
              <div className="field-header">
                <label htmlFor="amount-input" className="field-label">Amount</label>
                <span className="currency-hint">{fromInfo.name}</span>
              </div>
              <div className="amount-row">
                <span className="amount-symbol">{fromInfo.symbol || '$'}</span>
                <input
                  id="amount-input"
                  type="number"
                  step="any"
                  min="0.01"
                  placeholder="100.00"
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                  className="amount-input"
                  required
                />
                <span className="amount-code">{from}</span>
              </div>
              {/* Quick Amount Chips */}
              <div className="chips-row" aria-label="Quick amount presets">
                {QUICK_AMOUNTS.map((amt) => (
                  <button
                    type="button"
                    key={amt}
                    className={`chip-btn ${parseFloat(amount) === amt ? 'chip-active' : ''}`}
                    onClick={() => setAmount(amt.toString())}
                  >
                    +{amt.toLocaleString()}
                  </button>
                ))}
              </div>
            </div>

            {/* Currencies Section */}
            <div className="currencies-container">
              {/* From currency */}
              <div className="currency-field">
                <label htmlFor="from-select" className="field-label">From</label>
                <div className="select-box">
                  <span className="flag-icon">{fromInfo.flag}</span>
                  <select
                    id="from-select"
                    value={from}
                    onChange={(e) => setFrom(e.target.value)}
                    className="native-select"
                  >
                    {currencyOptions.map((c) => (
                      <option key={`from-${c.code}`} value={c.code}>
                        {c.code} — {c.name}
                      </option>
                    ))}
                  </select>
                </div>
              </div>

              {/* Swap Action */}
              <div className="swap-action">
                <button
                  type="button"
                  id="swap-currencies-btn"
                  className={`swap-action-btn ${isSwapping ? 'is-swapped' : ''}`}
                  onClick={handleSwap}
                  title="Swap currencies"
                  aria-label="Swap currencies"
                >
                  <span className="swap-icon">⇄</span>
                  <span className="swap-label">Swap</span>
                </button>
              </div>

              {/* To currency */}
              <div className="currency-field">
                <label htmlFor="to-select" className="field-label">To</label>
                <div className="select-box">
                  <span className="flag-icon">{toInfo.flag}</span>
                  <select
                    id="to-select"
                    value={to}
                    onChange={(e) => setTo(e.target.value)}
                    className="native-select"
                  >
                    {currencyOptions.map((c) => (
                      <option key={`to-${c.code}`} value={c.code}>
                        {c.code} — {c.name}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            </div>

            {/* Convert Button */}
            <button
              type="submit"
              id="convert-submit-btn"
              className="btn-primary"
              disabled={loading}
            >
              {loading ? 'Converting...' : `Convert ${from} to ${to}`}
            </button>
          </form>

          {/* Error Message */}
          {error && (
            <div className="error-box" role="alert">
              <span>⚠️</span>
              <span>{error}</span>
            </div>
          )}

          {/* Result Card */}
          {result && (
            <div className="result-card" id="conversion-result">
              <div className="result-top">
                <span className="result-equation">
                  {result.originAmount.toLocaleString()} {result.from} =
                </span>
                <button
                  type="button"
                  className="btn-copy"
                  onClick={handleCopyResult}
                  title="Copy result"
                >
                  {copied ? '✓ Copied' : 'Copy'}
                </button>
              </div>

              <div className="result-main-value">
                {result.convertedAmount.toLocaleString(undefined, {
                  minimumFractionDigits: 2,
                  maximumFractionDigits: 4,
                })} {result.to}
              </div>

              <div className="result-rates">
                <span>1 {result.from} = {result.rateUsed.toFixed(4)} {result.to}</span>
                <span className="dot-sep">•</span>
                <span>1 {result.to} = {(1 / result.rateUsed).toFixed(4)} {result.from}</span>
              </div>
            </div>
          )}
        </main>

        {/* History Section */}
        <section className="history-section" aria-labelledby="history-title">
          <div className="history-header">
            <div>
              <h2 id="history-title" className="section-title">Recent Conversions</h2>
              <p className="section-desc">Conversions saved to database</p>
            </div>
            <button
              type="button"
              className="btn-refresh"
              onClick={fetchHistory}
              disabled={historyLoading}
            >
              {historyLoading ? 'Refreshing...' : 'Refresh'}
            </button>
          </div>

          {history.length === 0 ? (
            <div className="empty-state">No conversions recorded yet.</div>
          ) : (
            <div className="history-cards-list">
              {history.map((item, idx) => (
                <div key={item.id || idx} className="history-item-card">
                  <div className="history-card-top">
                    <span className="pair-tag">{item.from} → {item.to}</span>
                    <span className="history-timestamp">{item.timestamp || 'Just now'}</span>
                  </div>
                  <div className="history-card-values">
                    <span className="val-text">
                      {item.originAmount?.toLocaleString()} {item.from} = <strong>{item.convertedAmount?.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 4 })} {item.to}</strong>
                    </span>
                    <span className="rate-text">Rate: {item.rateUsed?.toFixed(4)}</span>
                  </div>
                </div>
              ))}
            </div>
          )}
        </section>

        {/* Clean Footer */}
        <footer className="app-footer">
          <p>Currency Converter • Built with Spring Boot & React</p>
          <p className="footer-sub">Rates provided by Open Exchange Rates</p>
        </footer>
      </div>
    </div>
  );
}

export default Converter;
