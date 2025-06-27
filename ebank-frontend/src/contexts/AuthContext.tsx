import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { AuthContextType, AuthResponse, LoginRequest } from '../types';
import apiService from '../services/api';

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [user, setUser] = useState<AuthResponse | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Check if user is already logged in
    const storedUser = localStorage.getItem('user');
    const token = localStorage.getItem('token');
    
    if (storedUser && token && apiService.isAuthenticated()) {
      setUser(JSON.parse(storedUser));
    }
    
    setIsLoading(false);
  }, []);

  const login = async (credentials: LoginRequest): Promise<void> => {
    try {
      const authResponse = await apiService.login(credentials);
      
      // Store token and user data
      apiService.setAuthToken(authResponse.token);
      localStorage.setItem('user', JSON.stringify(authResponse));
      setUser(authResponse);
      
    } catch (error: any) {
      console.error('Login failed:', error);
      throw new Error(error.response?.data?.message || 'Login ou mot de passe erronés');
    }
  };

  const logout = () => {
    apiService.removeAuthToken();
    setUser(null);
    
    // Call logout endpoint (optional, since JWT is stateless)
    apiService.logout().catch(console.error);
  };

  const value: AuthContextType = {
    user,
    login,
    logout,
    isAuthenticated: !!user && apiService.isAuthenticated(),
    isLoading,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}; 