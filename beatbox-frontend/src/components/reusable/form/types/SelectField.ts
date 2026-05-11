import type { BaseField } from "./BaseField";

export type SelectField = BaseField & {
    type: "select";
    options: { label: string; value: string }[];
    placeholder?: string;
};