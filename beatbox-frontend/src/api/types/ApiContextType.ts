export type ApiContextType = {
    get<T>(endpoint: string): Promise<T>;
    post<T, D>(endpoint: string, data?: D): Promise<T>;
    patch<T, D>(endpoint: string, data?: D): Promise<T>;
    delete<T>(endpoint: string): Promise<T>;
};
