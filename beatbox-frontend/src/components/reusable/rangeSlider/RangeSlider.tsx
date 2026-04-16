import { useEffect, useRef } from "react";
import type {RangeSliderProps} from "./RangeSliderProps.ts";

export default function RangeSlider({
                                        value,
                                        min = 0,
                                        max = 100,
                                        step = 1,
                                        onChange,
                                        leftColor = "#CA3E47",
                                        rightColor = "#ccc"
                                    }: RangeSliderProps) {
    const ref = useRef<HTMLInputElement | null>(null);

    const updateBackground = () => {
        if (!ref.current) return;

        const percentage = ((value - min) / (max - min)) * 100;

        ref.current.style.background = `linear-gradient(
      to right,
      ${leftColor} ${percentage}%,
      ${rightColor} ${percentage}%
    )`;
    };

    useEffect(() => {
        updateBackground();
    }, [value]);

    return (
        <input
            ref={ref}
            type="range"
            min={min}
            max={max}
            step={step}
            value={value}
            onChange={(e) => onChange(parseFloat(e.target.value))}
        />
    );
}