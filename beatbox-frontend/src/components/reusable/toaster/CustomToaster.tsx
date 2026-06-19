import { useEffect, useState } from "react";
import { Toaster } from "react-hot-toast";

function useMedia() {
    const [width, setWidth] = useState<number>(
        typeof window !== "undefined" ? window.innerWidth : 1024
    );

    useEffect(() => {
        const onResize = () => setWidth(window.innerWidth);
        window.addEventListener("resize", onResize);
        return () => window.removeEventListener("resize", onResize);
    }, []);

    return {
        isMobile: width < 640,
        isTablet: width >= 640 && width < 1024,
    };
}

const CustomToaster = () => {
    const { isMobile, isTablet } = useMedia();

    const toastStyle: React.CSSProperties = {
        background: "var(--panel-2)",
        color: "var(--text)",
        border: "1px solid rgba(202, 62, 71, 0.35)",
        borderRadius: isMobile ? "10px" : "12px",
        padding: isMobile ? "10px 12px" : "14px 16px",
        fontSize: isMobile ? "13px" : "14px",
        lineHeight: 1.3,
        maxWidth: isMobile ? "92vw" : isTablet ? "420px" : "360px",
        boxShadow:
            "0 10px 30px rgba(0, 0, 0, 0.45), 0 0 0 1px rgba(202, 62, 71, 0.15)",
        wordBreak: "break-word",
    };

    return (
        <Toaster
            position={isMobile ? "top-center" : "top-left"}
            toastOptions={{
                duration: 4000,
                style: toastStyle,

                success: {
                    iconTheme: {
                        primary: "var(--accent)",
                        secondary: "var(--text)",
                    },
                },

                error: {
                    iconTheme: {
                        primary: "#ff5c67",
                        secondary: "var(--text)",
                    },
                },
            }}
        />
    );
}

export default CustomToaster;