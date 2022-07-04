import { useParams } from 'react-router-dom';
import { IUserUrlParams } from '../interfaces/url';
import { PAGE_USERS_TYPE } from '../constants';

export const useUsersPageInfo = (): keyof typeof PAGE_USERS_TYPE => {
  const urlParams = useParams<IUserUrlParams>();

  const { userId, action } = urlParams;

  if (userId && action === 'edit') {
    return 'USER_EDIT';
  } else if (userId) {
    return 'USER';
  }

  return 'USERS_LIST';
};
