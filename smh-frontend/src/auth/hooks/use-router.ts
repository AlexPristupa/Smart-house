import { AUTH_TAB } from 'auth/constants';
import { useHistory, useParams } from 'react-router-dom';
import { BASE_URLS } from 'services';

interface IAuthUrlParams {
  authType?: AUTH_TAB;
}

export const useRouter = () => {
  const history = useHistory();
  const params = useParams<IAuthUrlParams>();

  const goToTab = (tab: string) => history.push(`${BASE_URLS.AUTH}/${tab}`);

  return { history, params, goToTab };
};
