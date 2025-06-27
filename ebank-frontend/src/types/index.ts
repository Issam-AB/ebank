// User and Authentication Types
export enum UserRole {
  CLIENT = 'CLIENT',
  AGENT_GUICHET = 'AGENT_GUICHET'
}

export enum AccountStatus {
  OUVERT = 'OUVERT',
  BLOQUE = 'BLOQUE',
  CLOTURE = 'CLOTURE'
}

export enum OperationType {
  DEBIT = 'DEBIT',
  CREDIT = 'CREDIT'
}

export interface LoginRequest {
  login: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  tokenType: string;
  userId: number;
  login: string;
  nom: string;
  prenom: string;
  role: UserRole;
  expiresIn: number;
}

export interface ChangePasswordRequest {
  ancienMotDePasse: string;
  nouveauMotDePasse: string;
  confirmationMotDePasse: string;
}

// Client Management Types
export interface ClientRequest {
  nom: string;
  prenom: string;
  numeroIdentite: string;
  dateNaissance: string; // ISO date string
  email: string;
  adressePostale: string;
}

export interface ClientResponse {
  id: number;
  nom: string;
  prenom: string;
  numeroIdentite: string;
  dateNaissance: string;
  email: string;
  adressePostale: string;
  login: string;
  createdAt: string;
  message: string;
}

// Bank Account Types
export interface BankAccountRequest {
  rib: string;
  numeroIdentiteClient: string;
}

export interface BankAccountResponse {
  id: number;
  rib: string;
  solde: number;
  statut: AccountStatus;
  clientNom: string;
  clientPrenom: string;
  lastActivityAt: string;
  createdAt: string;
}

// Transfer Types
export interface VirementRequest {
  ribSource: string;
  montant: number;
  ribDestination: string;
  motif: string;
}

// Operation Types
export interface OperationResponse {
  id: number;
  intitule: string;
  type: OperationType;
  montant: number;
  motif: string;
  dateOperation: string;
  ribSource: string;
  ribDestination?: string;
  soldeAvant: number;
  soldeApres: number;
}

// Dashboard Types
export interface DashboardResponse {
  comptes: BankAccountResponse[];
  compteActuel: BankAccountResponse | null;
  operations: {
    content: OperationResponse[];
    totalElements: number;
    totalPages: number;
    first: boolean;
    last: boolean;
    size: number;
    number: number;
  };
  nomClient: string;
  prenomClient: string;
}

// API Response Wrapper
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
  error?: string;
}

// Auth Context Type
export interface AuthContextType {
  user: AuthResponse | null;
  login: (credentials: LoginRequest) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
  isLoading: boolean;
} 