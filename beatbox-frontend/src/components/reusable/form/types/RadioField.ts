import type { BaseField } from "./BaseField";

export type RadioField = BaseField & {
    type: "radio";
    options: { label: string; value: string }[];
};