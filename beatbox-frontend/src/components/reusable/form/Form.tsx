import styles from "./Form.module.css";
import type { FormProps } from "./types/FormProps";
import type { TextField } from "./types/TextField";
import type {SelectField} from "./types/SelectField.ts";
import type {RadioField} from "./types/RadioField.ts";
import type {SyntheticEvent} from "react";

// Usage:
// const FIELDS: FormField[] = [
//     {
//         type: "text",
//         name: "title",
//         label: "Title",
//         placeholder: "Enter a title",
//         required: true,
//     },
//     {
//         type: "textarea",
//         name: "description",
//         label: "Description",
//         placeholder: "Enter a description",
//     },
//     {
//         type: "select",
//         name: "category",
//         label: "Category",
//         placeholder: "Select a category",
//         required: true,
//         options: [
//             { label: "Art", value: "art" },
//             { label: "Photography", value: "photography" },
//             { label: "Design", value: "design" },
//         ],
//     },
//     {
//         type: "radio",
//         name: "visibility",
//         label: "Visibility",
//         required: true,
//         options: [
//             { label: "Public", value: "public" },
//             { label: "Private", value: "private" },
//         ],
//     },
//     {
//         type: "checkbox",
//         name: "terms",
//         label: "I agree to the terms and conditions",
//         required: true,
//     },
// ];

// SyntheticEvent wraps the native event and provides a consistent API across browsers
const Form = ({
                  fields,
                  values,
                  onChange,
                  onSubmit,
                  submitLabel = "Submit",
                  className,
              }: FormProps) => {
    const handleSubmit = (e: SyntheticEvent) => {
        e.preventDefault();
        onSubmit(values);
    };

    return (
        <form
            className={`${styles.form} ${className ?? ""}`}
            onSubmit={handleSubmit}
            noValidate={false}
        >
            {fields.map((field) => (
                <div key={field.name} className={styles.fieldGroup}>
                    {field.type !== "checkbox" && (
                        <label htmlFor={field.name} className={styles.label}>
                            {field.label}
                            {field.required && (
                                <span className={styles.required}>*</span>
                            )}
                        </label>
                    )}

                    {/* Text / Textarea */}
                    {(field.type === "text" ||
                        field.type === "email" ||
                        field.type === "password" ||
                        field.type === "number") && (
                        <input
                            id={field.name}
                            type={field.type}
                            name={field.name}
                            className={styles.input}
                            placeholder={(field as TextField).placeholder}
                            value={(values[field.name] as string) ?? ""}
                            required={field.required}
                            onChange={(e) =>
                                onChange(field.name, e.target.value)
                            }
                        />
                    )}

                    {field.type === "textarea" && (
                        <textarea
                            id={field.name}
                            name={field.name}
                            className={`${styles.input} ${styles.textarea}`}
                            placeholder={(field as TextField).placeholder}
                            value={(values[field.name] as string) ?? ""}
                            required={field.required}
                            rows={4}
                            onChange={(e) =>
                                onChange(field.name, e.target.value)
                            }
                        />
                    )}

                    {/* Select */}
                    {field.type === "select" && (
                        <select
                            id={field.name}
                            name={field.name}
                            className={`${styles.input} ${styles.select}`}
                            value={(values[field.name] as string) ?? ""}
                            required={field.required}
                            onChange={(e) =>
                                onChange(field.name, e.target.value)
                            }
                        >
                            {(field as SelectField).placeholder && (
                                <option value="" disabled>
                                    {(field as SelectField).placeholder}
                                </option>
                            )}
                            {(field as SelectField).options.map((opt) => (
                                <option key={opt.value} value={opt.value}>
                                    {opt.label}
                                </option>
                            ))}
                        </select>
                    )}

                    {/* Checkbox */}
                    {field.type === "checkbox" && (
                        <label
                            htmlFor={field.name}
                            className={styles.checkboxLabel}
                        >
                            <input
                                id={field.name}
                                type="checkbox"
                                name={field.name}
                                className={styles.checkbox}
                                checked={(values[field.name] as boolean) ?? false}
                                required={field.required}
                                onChange={(e) =>
                                    onChange(field.name, e.target.checked)
                                }
                            />
                            <span className={styles.checkboxText}>
                                {field.label}
                                {field.required && (
                                    <span className={styles.required}>*</span>
                                )}
                            </span>
                        </label>
                    )}

                    {/* Radio */}
                    {field.type === "radio" && (
                        <div className={styles.radioGroup}>
                            {(field as RadioField).options.map((opt) => (
                                <label
                                    key={opt.value}
                                    className={styles.radioLabel}
                                >
                                    <input
                                        type="radio"
                                        name={field.name}
                                        value={opt.value}
                                        className={styles.radio}
                                        checked={
                                            values[field.name] === opt.value
                                        }
                                        required={field.required}
                                        onChange={(e) =>
                                            onChange(field.name, e.target.value)
                                        }
                                    />
                                    <span className={styles.radioText}>
                                        {opt.label}
                                    </span>
                                </label>
                            ))}
                        </div>
                    )}
                </div>
            ))}

            <button type="submit" className={styles.submitButton}>
                {submitLabel}
            </button>
        </form>
    );
};

export default Form;