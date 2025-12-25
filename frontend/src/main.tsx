import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { Providers } from "./lib/providers.tsx";
import { BrowserRouter } from "react-router-dom";
import "./styles/scrollbar.css";
import { AuthGuard } from "./lib/AuthGuard.tsx";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Providers>
      <BrowserRouter>
        <AuthGuard>
          <App />
        </AuthGuard>
      </BrowserRouter>
    </Providers>
  </StrictMode>
);
