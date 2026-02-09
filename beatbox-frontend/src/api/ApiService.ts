import axios, {
    type AxiosInstance,
    type AxiosRequestConfig,
    type AxiosResponse,
    type InternalAxiosRequestConfig,
} from "axios";

export default class ApiService {
    private readonly baseURL: string;
    private client: AxiosInstance;
    private readonly getToken: () => Promise<string | undefined>;

    constructor(getToken: () => Promise<string | undefined>) {
        this.baseURL = import.meta.env.VITE_API_BASE_URL as string;
        this.getToken = getToken;

        this.client = axios.create({
            baseURL: this.baseURL,
        });

        this.client.interceptors.request.use(
            async (config: InternalAxiosRequestConfig) => {
                const token = await this.getToken();
                if (token) {
                    config.headers.Authorization = `Bearer ${token}`;
                }
                return config;
            }
        );
    }

    async request<T = unknown>(config: AxiosRequestConfig): Promise<T> {
        try {
            const response: AxiosResponse<T> = await this.client.request<T>(config);
            return response.data;
        } catch (err: unknown) {
            console.error("API Request failed:", err);
            throw new Error("Api request failed.");
        }
    }

    get<T = unknown>(endpoint: string, config?: AxiosRequestConfig): Promise<T> {
        return this.request<T>({ method: "GET", url: endpoint, ...config });
    }

    post<T = unknown, D = unknown>(
        endpoint: string,
        data?: D,
        config?: AxiosRequestConfig
    ): Promise<T> {
        return this.request<T>({ method: "POST", url: endpoint, data, ...config });
    }

    patch<T = unknown, D = unknown>(
        endpoint: string,
        data?: D,
        config?: AxiosRequestConfig
    ): Promise<T> {
        return this.request<T>({ method: "PATCH", url: endpoint, data, ...config });
    }

    delete<T = unknown>(
        endpoint: string,
        config?: AxiosRequestConfig
    ): Promise<T> {
        return this.request<T>({ method: "DELETE", url: endpoint, ...config });
    }
}
