export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        main: "#FF8C00",
        text: "#B5B5B5",
        primary: "#333333",
        secondary: "#666666",
        tertiary: "#888888",
        bg: "#FAFAFA",
        bgsecondary: "#d9d9d9",
        card: "#ffffff",
        meta: "#767676",
      },
      fontFamily: {
        sans: ["Pretendard", "ui-sans-serif", "system-ui"],
      },
      fontSize: {
        "nav-items": ["24px", { lineHeight: "32px" }],
        "card-title": ["24px", { lineHeight: "32px" }],
        "card-content": ["14px", { lineHeight: "20px" }],
        "card-meta": ["12px", { lineHeight: "16px" }],
      },
    },
  },
  plugins: [],
};
