import type {AxiosRequestConfig} from "axios";

export type ApiContextType = {
    get<T>(endpoint: string, config?: AxiosRequestConfig): Promise<T>;
    post<T, D>(endpoint: string, data?: D, config?: AxiosRequestConfig): Promise<T>;
    patch<T, D>(endpoint: string, data?: D, config?: AxiosRequestConfig): Promise<T>;
    delete<T>(endpoint: string, config?: AxiosRequestConfig): Promise<T>;
};