import { PAGE_USERS_TYPE } from '../constants';
import { UserList } from './user-list';
import { CardUser } from './user-list/card-user';
import { EditUser } from './user-list/edit-user';

export const PAGE_USERS_CONTENT: Record<keyof typeof PAGE_USERS_TYPE, React.FC> = {
  USERS_LIST: UserList,
  USER: CardUser,
  USER_EDIT: EditUser,
};
