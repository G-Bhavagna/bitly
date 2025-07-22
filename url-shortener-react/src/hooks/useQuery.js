import { useQuery } from '@tanstack/react-query';
import api from '../api/api';

export const useFetchTotalClicks = (token, onError) => {
  return useQuery({
    queryKey: ['url-totalclick'],
    queryFn: async () => {
      const response = await api.get(
        '/api/urls/totalClicks?startDate=2024-12-24&endDate=2025-12-24',
        {
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response;
    },
    select: (data) => {
      return Object.keys(data.data).map((key) => ({
        clickedDate: key,
        count: data.data[key],
      }));
    },
    onError,
    staleTime: 5000, // optional cache time
  });
};
