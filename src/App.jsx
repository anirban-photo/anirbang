import "./hero.css";

export default function App() {
  return (
    <>
      {/* HERO SECTION */}
      <section className="hero">
        <div className="overlay"></div>

        <div className="hero-content">
          <h1 className="title">ANIRBAN GHOSAL</h1>
          <p className="subtitle">Cinematic Photography & Videography</p>

          <div className="actions">
            <button
              className="btn primary"
              onClick={() => {
                document.getElementById("portfolio")?.scrollIntoView({
                  behavior: "smooth",
                });
              }}
            >
              View Work
            </button>

            <button
              className="btn ghost"
              onClick={() => {
                document.getElementById("contact")?.scrollIntoView({
                  behavior: "smooth",
                });
              }}
            >
              Contact
            </button>
          </div>
        </div>
      </section>

      {/* PORTFOLIO SECTION */}
      <section
        id="portfolio"
        style={{
          minHeight: "100vh",
          background: "#111",
          color: "#fff",
          padding: "100px 40px",
        }}
      >
        <h2>Portfolio Section</h2>
        <p>এখানে তোমার কাজের ভিডিও / ছবি যাবে</p>
      </section>

      {/* CONTACT SECTION */}
      {/* PORTFOLIO – REEL STYLE */}
      <section id="portfolio" className="portfolio">
        <h2 className="portfolio-title">Featured Reels</h2>

        <div className="reel-grid">
          <div className="reel-card">
            <div className="reel-overlay">Wedding Film</div>
          </div>

          <div className="reel-card">
            <div className="reel-overlay">Cinematic Pre-Wedding</div>
          </div>

          <div className="reel-card">
            <div className="reel-overlay">Music Video</div>
          </div>

          <div className="reel-card">
            <div className="reel-overlay">Short Film</div>
          </div>

          <div className="reel-card">
            <div className="reel-overlay">Reels / Ads</div>
          </div>

          <div className="reel-card">
            <div className="reel-overlay">Documentary</div>
          </div>
        </div>
      </section>
    </>
  );
}