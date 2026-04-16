export type RangeSliderProps = {
    value: number;
    min?: number;
    max?: number;
    step?: number;
    onChange: (value: number) => void;
    leftColor?: string;
    rightColor?: string;
};