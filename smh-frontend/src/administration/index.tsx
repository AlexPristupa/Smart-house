import { MainLayout } from 'ui';
import { BASE_URLS } from 'services';
import { Route } from 'react-router-dom';
import { UsersPage } from './view/users';
import { Building } from 'building';
import { TypeList } from './view/manual/types';

export const AdministrationPage: React.FC = () => {
  return (
    <MainLayout>
      <Route exact path={`${BASE_URLS.BUILDINGS}/:buildingId?/:contents?/:contentId?/:action?`} component={Building} />
      <Route exact path={BASE_URLS.MANUAL} component={TypeList} />
      <Route exact path={`${BASE_URLS.USERS}/:userId?/:action?`} component={UsersPage} />
    </MainLayout>
  );
};
