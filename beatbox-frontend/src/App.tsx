import {BrowserRouter, Route, Routes} from "react-router";
import Home from "./pages/home/Home.tsx";
import Layout from "./components/layout/Layout.tsx";
import Upload from "./pages/upload/Upload.tsx";
import Feed from "./pages/feed/Feed.tsx";

// Global CSS imports
import "./globalCss/Swiper.css"

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<Layout />}>
                    <Route path="/" element={<Home />} />
                    <Route path="/upload" element={<Upload />} />
                    <Route path="/feed" element={<Feed />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}
