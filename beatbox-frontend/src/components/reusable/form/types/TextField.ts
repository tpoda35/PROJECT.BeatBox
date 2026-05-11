import type {BaseField} from "./BaseField.ts";

export type TextField = BaseField & {
    type: "text" | "email" | "password" | "number" | "textarea";
    placeholder?: string;
};