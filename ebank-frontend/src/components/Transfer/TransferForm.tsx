import React, { useState, useEffect } from 'react';
import {
  Box,
  Card,
  CardContent,
  TextField,
  Button,
  Typography,
  Alert,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  CircularProgress,
  Divider,
} from '@mui/material';
import { Send, AccountBalance } from '@mui/icons-material';
import { VirementRequest, BankAccountResponse } from '../../types';
import apiService from '../../services/api';

const TransferForm: React.FC = () => {
  const [formData, setFormData] = useState<VirementRequest>({
    ribSource: '',
    montant: 0,
    ribDestination: '',
    motif: '',
  });
  const [accounts, setAccounts] = useState<BankAccountResponse[]>([]);
  const [loading, setLoading] = useState(false);
  const [accountsLoading, setAccountsLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = async () => {
    try {
      setAccountsLoading(true);
      const dashboard = await apiService.getDashboard();
      setAccounts(dashboard.comptes);
      if (dashboard.comptes.length > 0) {
        setFormData(prev => ({
          ...prev,
          ribSource: dashboard.comptes[0].rib
        }));
      }
    } catch (err: any) {
      setError('Erreur lors du chargement des comptes');
    } finally {
      setAccountsLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'montant' ? parseFloat(value) || 0 : value,
    }));
    // Clear messages when user starts typing
    if (error) setError('');
    if (success) setSuccess('');
  };

  const handleAccountChange = (rib: string) => {
    setFormData(prev => ({
      ...prev,
      ribSource: rib,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setSuccess('');

    try {
      // Validation
      if (formData.ribSource === formData.ribDestination) {
        throw new Error('Le compte source et destinataire ne peuvent pas être identiques');
      }

      if (formData.montant <= 0) {
        throw new Error('Le montant doit être supérieur à 0');
      }

      await apiService.performTransfer(formData);
      setSuccess('Virement effectué avec succès');
      
      // Reset form except source account
      setFormData(prev => ({
        ...prev,
        montant: 0,
        ribDestination: '',
        motif: '',
      }));

      // Reload accounts to update balances
      loadAccounts();
      
    } catch (err: any) {
      setError(err.response?.data?.message || err.message || 'Erreur lors du virement');
    } finally {
      setLoading(false);
    }
  };

  const getSelectedAccount = () => {
    return accounts.find(account => account.rib === formData.ribSource);
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('fr-FR', {
      style: 'currency',
      currency: 'EUR'
    }).format(amount);
  };

  if (accountsLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  if (accounts.length === 0) {
    return (
      <Alert severity="warning">
        Aucun compte disponible pour effectuer un virement
      </Alert>
    );
  }

  const selectedAccount = getSelectedAccount();

  return (
    <Box maxWidth="lg" mx="auto">
      <Typography variant="h4" component="h1" gutterBottom>
        Nouveau virement
      </Typography>

      <Box display="flex" flexDirection={{ xs: 'column', md: 'row' }} gap={3}>
        <Box flex="2" minWidth="400px">
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Détails du virement
              </Typography>

              {error && (
                <Alert severity="error" sx={{ mb: 2 }}>
                  {error}
                </Alert>
              )}

              {success && (
                <Alert severity="success" sx={{ mb: 2 }}>
                  {success}
                </Alert>
              )}

              <form onSubmit={handleSubmit}>
                <FormControl fullWidth margin="normal">
                  <InputLabel>Compte source</InputLabel>
                  <Select
                    value={formData.ribSource}
                    label="Compte source"
                    onChange={(e) => handleAccountChange(e.target.value)}
                    disabled={loading}
                  >
                    {accounts.map((account) => (
                      <MenuItem key={account.rib} value={account.rib}>
                        {account.rib} - {formatCurrency(account.solde)}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

                {/* Montant input or warning if balance is zero */}
                {selectedAccount?.solde === 0 ? (
                  <Alert severity="warning" sx={{ my: 2 }}>
                    Solde insuffisant pour effectuer un virement.
                  </Alert>
                ) : (
                  <TextField
                    fullWidth
                    label="Montant (€)"
                    name="montant"
                    type="number"
                    value={formData.montant || ''}
                    onChange={handleChange}
                    margin="normal"
                    required
                    disabled={loading}
                    inputProps={{ 
                      min: 0.01, 
                      step: 0.01,
                      max: selectedAccount?.solde || 0
                    }}
                  />
                )}

                <TextField
                  fullWidth
                  label="RIB destinataire"
                  name="ribDestination"
                  value={formData.ribDestination}
                  onChange={handleChange}
                  margin="normal"
                  required
                  disabled={loading}
                  helperText="Saisissez le RIB du compte destinataire"
                />

                <TextField
                  fullWidth
                  label="Motif"
                  name="motif"
                  value={formData.motif}
                  onChange={handleChange}
                  margin="normal"
                  required
                  disabled={loading}
                  multiline
                  rows={3}
                  helperText="Décrivez le motif du virement"
                />

                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  size="large"
                  disabled={loading || !formData.ribSource || selectedAccount?.solde === 0}
                  sx={{
                    mt: 3,
                    py: 1.5,
                    background: 'linear-gradient(45deg, #4CAF50, #8BC34A)',
                    '&:hover': {
                      background: 'linear-gradient(45deg, #45a049, #7cb342)',
                    },
                  }}
                  startIcon={loading ? <CircularProgress size={20} /> : <Send />}
                >
                  {loading ? 'Envoi en cours...' : 'Effectuer le virement'}
                </Button>
              </form>
            </CardContent>
          </Card>
        </Box>

        <Box flex="1" minWidth="300px">
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Récapitulatif
              </Typography>
              
              {selectedAccount && (
                <Box>
                  <Box display="flex" alignItems="center" mb={2}>
                    <AccountBalance color="primary" sx={{ mr: 1 }} />
                    <Typography variant="subtitle1">
                      Compte source
                    </Typography>
                  </Box>
                  
                  <Typography variant="body2" color="text.secondary" gutterBottom>
                    RIB: {selectedAccount.rib}
                  </Typography>
                  
                  <Typography variant="body2" color="text.secondary" gutterBottom>
                    Solde disponible: {formatCurrency(selectedAccount.solde)}
                  </Typography>

                  <Divider sx={{ my: 2 }} />

                  <Typography variant="body2" color="text.secondary" gutterBottom>
                    Montant à transférer: {formatCurrency(formData.montant)}
                  </Typography>
                  
                  <Typography variant="body2" sx={{ fontWeight: 'bold' }}>
                    Solde après virement: {formatCurrency(selectedAccount.solde - formData.montant)}
                  </Typography>
                </Box>
              )}
            </CardContent>
          </Card>

          {/* <Alert severity="info" sx={{ mt: 2 }}>
            <Typography variant="body2">
              <strong>Rappel:</strong> Vérifiez soigneusement le RIB destinataire avant de confirmer le virement.
            </Typography>
          </Alert> */}
        </Box>
      </Box>
    </Box>
  );
};

export default TransferForm; 