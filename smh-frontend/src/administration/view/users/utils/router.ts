import { useParams, useHistory } from 'react-router-dom';
import { IUserUrlParams } from '../interfaces/url';
import { BASE_URLS } from 'services';

export const useUsersRouter = () => {
  const params = useParams<IUserUrlParams>();
  const history = useHistory();

  const goToUserList = () => history.push(`${BASE_URLS.USERS}`);

  const gotToUserEdit = (userId?: number) => history.push(`${BASE_URLS.USERS}/${userId}/edit`);

  const goToUserPage = (userId?: number) => history.push(`${BASE_URLS.USERS}/${userId}`);

  return {
    params,
    history,
    goToUserList,
    gotToUserEdit,
    goToUserPage,
  };
};
