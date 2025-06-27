import React, { useState, useEffect, useCallback } from 'react';
import {
  Box,
  Card,
  CardContent,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Chip,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Pagination,
  CircularProgress,
  Alert,
  IconButton,
} from '@mui/material';
import { Refresh, AccountBalance, TrendingUp, TrendingDown } from '@mui/icons-material';
import { DashboardResponse, OperationType } from '../../types';
import apiService from '../../services/api';

const Dashboard: React.FC = () => {
  const [dashboard, setDashboard] = useState<DashboardResponse | null>(null);
  const [selectedRib, setSelectedRib] = useState<string>('');
  const [currentPage, setCurrentPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const pageSize = 10;

  const loadDashboard = useCallback(async (rib?: string, page: number = 0) => {
    try {
      setLoading(true);
      const data = await apiService.getDashboard(rib, page, pageSize);
      setDashboard(data);
      if (!selectedRib && data.compteActuel) {
        setSelectedRib(data.compteActuel.rib);
      }
    } catch (err: any) {
      setError(err.response?.data?.message || 'Erreur lors du chargement du tableau de bord');
    } finally {
      setLoading(false);
    }
  }, [selectedRib]);

  useEffect(() => {
    loadDashboard();
  }, [loadDashboard]);

  const handleAccountChange = (rib: string) => {
    setSelectedRib(rib);
    setCurrentPage(0);
    loadDashboard(rib, 0);
  };

  const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
    const page = value - 1; // Material-UI pagination is 1-indexed
    setCurrentPage(page);
    loadDashboard(selectedRib, page);
  };

  const handleRefresh = () => {
    loadDashboard(selectedRib, currentPage);
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('fr-FR', {
      style: 'currency',
      currency: 'EUR'
    }).format(amount);
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleString('fr-FR');
  };

  const getOperationIcon = (type: OperationType) => {
    return type === OperationType.CREDIT ? 
      <TrendingUp color="success" /> : 
      <TrendingDown color="error" />;
  };

  const getOperationColor = (type: OperationType) => {
    return type === OperationType.CREDIT ? 'success' : 'error';
  };

  if (loading && !dashboard) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Alert severity="error" sx={{ mb: 2 }}>
        {error}
      </Alert>
    );
  }

  if (!dashboard) {
    return (
      <Alert severity="info" sx={{ mb: 2 }}>
        Aucune donnée disponible
      </Alert>
    );
  }

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1">
          Bienvenue, {dashboard.prenomClient} {dashboard.nomClient}
        </Typography>
        <IconButton onClick={handleRefresh} disabled={loading}>
          <Refresh />
        </IconButton>
      </Box>

      {/* Account Selection */}
      {dashboard.comptes.length > 1 && (
        <Box mb={3}>
          <FormControl fullWidth>
            <InputLabel>Sélectionner un compte</InputLabel>
            <Select
              value={selectedRib}
              label="Sélectionner un compte"
              onChange={(e) => handleAccountChange(e.target.value)}
            >
              {dashboard.comptes.map((account) => (
                <MenuItem key={account.rib} value={account.rib}>
                  {account.rib} - {formatCurrency(account.solde)}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
      )}

      {/* Account Information Cards */}
      <Box display="flex" flexWrap="wrap" gap={3} mb={3}>
        <Box flex="1" minWidth="300px">
          <Card sx={{ background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)' }}>
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <AccountBalance sx={{ color: 'white', mr: 1 }} />
                <Typography variant="h6" sx={{ color: 'white' }}>
                  Numéro de RIB
                </Typography>
              </Box>
              <Typography variant="h4" sx={{ color: 'white', fontWeight: 'bold' }}>
                {dashboard.compteActuel?.rib}
              </Typography>
            </CardContent>
          </Card>
        </Box>

        <Box flex="1" minWidth="300px">
          <Card sx={{ background: 'linear-gradient(45deg, #4CAF50 30%, #8BC34A 90%)' }}>
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <TrendingUp sx={{ color: 'white', mr: 1 }} />
                <Typography variant="h6" sx={{ color: 'white' }}>
                  Solde du compte
                </Typography>
              </Box>
              <Typography variant="h4" sx={{ color: 'white', fontWeight: 'bold' }}>
                {dashboard.compteActuel ? formatCurrency(dashboard.compteActuel.solde) : '0,00 €'}
              </Typography>
            </CardContent>
          </Card>
        </Box>
      </Box>

      {/* Operations Table */}
      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Dernières opérations
          </Typography>
          
          {loading ? (
            <Box display="flex" justifyContent="center" p={3}>
              <CircularProgress />
            </Box>
          ) : dashboard.operations?.content?.length > 0 ? (
            <>
              <TableContainer component={Paper} elevation={0}>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>Date</TableCell>
                      <TableCell>Intitulé</TableCell>
                      <TableCell>Type</TableCell>
                      <TableCell>Montant</TableCell>
                      <TableCell>Solde après</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {dashboard.operations.content.map((operation) => (
                      <TableRow key={operation.id}>
                        <TableCell>
                          {formatDate(operation.dateOperation)}
                        </TableCell>
                        <TableCell>
                          <Box display="flex" alignItems="center">
                            {getOperationIcon(operation.type)}
                            <Typography variant="body2" sx={{ ml: 1 }}>
                              {operation.intitule}
                            </Typography>
                          </Box>
                        </TableCell>
                        <TableCell>
                          <Chip
                            label={operation.type}
                            color={getOperationColor(operation.type)}
                            size="small"
                          />
                        </TableCell>
                        <TableCell>
                          <Typography
                            variant="body2"
                            sx={{
                              color: operation.type === OperationType.CREDIT ? 'success.main' : 'error.main',
                              fontWeight: 'bold'
                            }}
                          >
                            {operation.type === OperationType.CREDIT ? '+' : '-'}
                            {formatCurrency(operation.montant)}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          {formatCurrency(operation.soldeApres)}
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>

              {/* Pagination */}
              {dashboard.operations.totalPages > 1 && (
                <Box display="flex" justifyContent="center" mt={3}>
                  <Pagination
                    count={dashboard.operations.totalPages}
                    page={currentPage + 1}
                    onChange={handlePageChange}
                    color="primary"
                  />
                </Box>
              )}
            </>
          ) : (
            <Typography variant="body2" color="text.secondary" textAlign="center" py={3}>
              Aucune opération disponible
            </Typography>
          )}
        </CardContent>
      </Card>
    </Box>
  );
};

export default Dashboard; 