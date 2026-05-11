import type {FormField} from "./FormField.ts";
import type { FormValues } from "./FormValues.ts";

export type FormProps = {
    fields: FormField[];
    values: FormValues;
    onChange: (name: string, value: string | boolean) => void;
    onSubmit: (values: FormValues) => void;
    submitLabel?: string;
    className?: string;
};