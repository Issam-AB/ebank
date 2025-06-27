import React, { useState } from 'react';
import {
  Box,
  Card,
  CardContent,
  TextField,
  Button,
  Typography,
  Alert,
  CircularProgress,
  InputAdornment,
} from '@mui/material';
import { AccountBalance, Badge, Save } from '@mui/icons-material';
import { apiService } from '../../services/api';
import { BankAccountRequest } from '../../types';

const AddAccountForm: React.FC = () => {
  const [formData, setFormData] = useState<BankAccountRequest>({
    rib: '',
    numeroIdentiteClient: '',
  });

  const [errors, setErrors] = useState<Partial<BankAccountRequest>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const validateForm = (): boolean => {
    const newErrors: Partial<BankAccountRequest> = {};

    if (!formData.rib.trim()) {
      newErrors.rib = 'Le RIB est obligatoire';
    } else if (!/^[A-Z]{2}[0-9]{2}[A-Z0-9]{4}[0-9]{7}([A-Z0-9]?){0,16}$/.test(formData.rib.replace(/\s/g, ''))) {
      newErrors.rib = 'Format de RIB invalide';
    }

    if (!formData.numeroIdentiteClient.trim()) {
      newErrors.numeroIdentiteClient = 'Le numéro d\'identité du client est obligatoire';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));

    // Clear error when user starts typing
    if (errors[name as keyof BankAccountRequest]) {
      setErrors(prev => ({
        ...prev,
        [name]: undefined,
      }));
    }

    // Clear messages
    if (successMessage) setSuccessMessage('');
    if (errorMessage) setErrorMessage('');
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    setIsLoading(true);
    setErrorMessage('');
    setSuccessMessage('');

    try {
      const response = await apiService.createBankAccount(formData);
      setSuccessMessage(`Compte bancaire créé avec succès! RIB: ${response.rib}, Statut: ${response.statut}`);
      setFormData({
        rib: '',
        numeroIdentiteClient: '',
      });
    } catch (err: any) {
      setErrorMessage(err.response?.data?.error || err.message || 'Erreur lors de la création du compte');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Box sx={{ maxWidth: 600, mx: 'auto', p: 3 }}>
      <Card sx={{ boxShadow: 3, borderRadius: 2 }}>
        <CardContent sx={{ p: 4 }}>
          <Box textAlign="center" mb={4}>
            <Typography
              variant="h4"
              component="h1"
              gutterBottom
              sx={{
                fontWeight: 'bold',
                color: '#333',
                background: 'linear-gradient(45deg, #667eea, #764ba2)',
                backgroundClip: 'text',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
              }}
            >
              Créer un nouveau compte bancaire
            </Typography>
            <Typography variant="subtitle1" color="text.secondary">
              Saisissez le RIB et l'identité du client pour créer un nouveau compte
            </Typography>
          </Box>

          {successMessage && (
            <Alert severity="success" sx={{ mb: 3 }}>
              {successMessage}
            </Alert>
          )}

          {errorMessage && (
            <Alert severity="error" sx={{ mb: 3 }}>
              {errorMessage}
            </Alert>
          )}

          <form onSubmit={handleSubmit}>
            <Box sx={{ mb: 3 }}>
              <TextField
                fullWidth
                label="RIB (Relevé d'Identité Bancaire)"
                name="rib"
                value={formData.rib}
                onChange={handleChange}
                error={!!errors.rib}
                helperText={errors.rib || 'Format: FR14 2004 1010 0505 0001 3M02 606'}
                disabled={isLoading}
                placeholder="FR1420041010050500013M02606"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <AccountBalance color="primary" />
                    </InputAdornment>
                  ),
                }}
              />
            </Box>

            <Box sx={{ mb: 4 }}>
              <TextField
                fullWidth
                label="Numéro d'identité du client"
                name="numeroIdentiteClient"
                value={formData.numeroIdentiteClient}
                onChange={handleChange}
                error={!!errors.numeroIdentiteClient}
                helperText={errors.numeroIdentiteClient || 'Le numéro d\'identité doit exister dans la base de données'}
                disabled={isLoading}
                placeholder="CLIENT001"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Badge color="primary" />
                    </InputAdornment>
                  ),
                }}
              />
            </Box>

            <Box textAlign="center">
              <Button
                type="submit"
                variant="contained"
                size="large"
                disabled={isLoading}
                startIcon={isLoading ? <CircularProgress size={20} /> : <Save />}
                sx={{
                  px: 4,
                  py: 1.5,
                  background: 'linear-gradient(45deg, #667eea, #764ba2)',
                  '&:hover': {
                    background: 'linear-gradient(45deg, #5a6fd8, #6a4190)',
                  },
                }}
              >
                {isLoading ? 'Création en cours...' : 'Créer le compte'}
              </Button>
            </Box>
          </form>
        </CardContent>
      </Card>
    </Box>
  );
};

export default AddAccountForm; 