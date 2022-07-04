import { useMemo } from 'react';
import { PAGE_USERS_CONTENT } from './views';
import { useUsersPageInfo } from './utils';

export const UsersPage: React.FC = () => {
  const pageType = useUsersPageInfo();

  const Content = useMemo(() => PAGE_USERS_CONTENT[pageType], [pageType]);

  return <Content />;
};
