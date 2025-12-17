import { Routes, Route } from "react-router-dom";
import { TrendPage } from "./recipe/pages/TrandPage";
import { RecipeLayout } from "./recipe/RecipeLayout";

function App() {
  return (
    <div className="flex min-h-screen bg-bg">
      <main className="flex-1">
        <Routes>
          <Route element={<RecipeLayout />}>
            <Route path="/" element={<TrendPage />} />
            <Route path="/chef" element={<TrendPage />} />
          </Route>
        </Routes>
      </main>
    </div>
  );
}

export default App;
