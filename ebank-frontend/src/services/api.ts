import axios, { AxiosInstance, AxiosResponse } from 'axios';
import {
  ApiResponse,
  LoginRequest,
  AuthResponse,
  ChangePasswordRequest,
  ClientRequest,
  ClientResponse,
  BankAccountRequest,
  BankAccountResponse,
  VirementRequest,
  DashboardResponse,
  OperationResponse
} from '../types';

class ApiService {
  private api: AxiosInstance;
  private baseURL = 'http://localhost:8080/api';

  constructor() {
    this.api = axios.create({
      baseURL: this.baseURL,
      headers: {
        'Content-Type': 'application/json',
      },
    });

    // Add request interceptor to include JWT token
    this.api.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('token');
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );

    // Add response interceptor to handle token expiration
    this.api.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response?.status === 401) {
          // Token expired or invalid
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          window.location.href = '/login';
        }
        return Promise.reject(error);
      }
    );
  }

  // Authentication methods
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response: AxiosResponse<ApiResponse<AuthResponse>> = await this.api.post(
      '/auth/login',
      credentials
    );
    return response.data.data;
  }

  async changePassword(request: ChangePasswordRequest): Promise<void> {
    await this.api.post('/auth/change-password', request);
  }

  async logout(): Promise<void> {
    await this.api.post('/auth/logout');
  }

  // Client management methods (AGENT_GUICHET only)
  async createClient(request: ClientRequest): Promise<ClientResponse> {
    const response: AxiosResponse<ApiResponse<ClientResponse>> = await this.api.post(
      '/clients',
      request
    );
    return response.data.data;
  }

  // Bank account management methods (AGENT_GUICHET only)
  async createBankAccount(request: BankAccountRequest): Promise<BankAccountResponse> {
    const response: AxiosResponse<ApiResponse<BankAccountResponse>> = await this.api.post(
      '/accounts/create',
      request
    );
    return response.data.data;
  }

  async getBankAccountByRib(rib: string): Promise<BankAccountResponse> {
    const response: AxiosResponse<ApiResponse<BankAccountResponse>> = await this.api.get(
      `/accounts/${rib}`
    );
    return response.data.data;
  }

  // Dashboard methods (CLIENT only)
  async getDashboard(rib?: string, page: number = 0, size: number = 10): Promise<DashboardResponse> {
    const params = new URLSearchParams();
    if (rib) params.append('rib', rib);
    params.append('page', page.toString());
    params.append('size', size.toString());

    const response: AxiosResponse<ApiResponse<DashboardResponse>> = await this.api.get(
      `/dashboard?${params.toString()}`
    );
    return response.data.data;
  }

  async getAccountOperations(
    rib: string,
    page: number = 0,
    size: number = 10
  ): Promise<{ content: OperationResponse[]; totalElements: number; totalPages: number }> {
    const response: AxiosResponse<ApiResponse<any>> = await this.api.get(
      `/dashboard/operations/${rib}?page=${page}&size=${size}`
    );
    return response.data.data;
  }

  // Transfer methods (CLIENT only)
  async performTransfer(request: VirementRequest): Promise<void> {
    await this.api.post('/transfers', request);
  }

  // Utility methods
  setAuthToken(token: string): void {
    localStorage.setItem('token', token);
  }

  removeAuthToken(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

  getAuthToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    const token = this.getAuthToken();
    if (!token) return false;

    try {
      // Check if token is expired (basic check)
      const payload = JSON.parse(atob(token.split('.')[1]));
      const currentTime = Date.now() / 1000;
      return payload.exp > currentTime;
    } catch (error) {
      return false;
    }
  }
}

export const apiService = new ApiService();
export default apiService; 