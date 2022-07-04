import { AuthPage } from 'auth';
import { useState } from 'react';
import { Redirect, Route, Router, Switch } from 'react-router-dom';
import { BASE_URLS, getIsUserAuthenticated, history, PrivateRoute, useLocalStorageChange } from 'services';
import { MainLayout, NotFoundPage } from 'ui';
import { AdministrationPage } from './administration';
import { BuildingPage } from 'building';
import { NotificationsList } from 'building/views/notifications-list';
import { EditProfileForm, ProfilePage } from 'profile/views';
import { QueryClient, QueryClientProvider } from 'react-query';

export const queryClient = new QueryClient();

export const App: React.FC = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(getIsUserAuthenticated());

  useLocalStorageChange(() => setIsAuthenticated(getIsUserAuthenticated()));

  return (
    <QueryClientProvider client={queryClient}>
      <Router history={history}>
        <Switch>
          <Redirect exact from="/" to={BASE_URLS.BUILDINGS} />
          <Route exact path={`${BASE_URLS.AUTH}/:authType?`} component={AuthPage} />
          <PrivateRoute isAuthenticated={isAuthenticated} exact path={BASE_URLS.NOTIFICATIONS}>
            <MainLayout>
              <NotificationsList />
            </MainLayout>
          </PrivateRoute>

          <PrivateRoute isAuthenticated={isAuthenticated} exact path={BASE_URLS.PROFILE}>
            <MainLayout>
              <ProfilePage />
            </MainLayout>
          </PrivateRoute>
          <PrivateRoute isAuthenticated={isAuthenticated} exact path={`${BASE_URLS.PROFILE}/edit`}>
            <MainLayout>
              <EditProfileForm />
            </MainLayout>
          </PrivateRoute>
          <PrivateRoute isAuthenticated={isAuthenticated} path="/:headerTab?">
            <AdministrationPage />
          </PrivateRoute>
          <PrivateRoute
            isAuthenticated={isAuthenticated}
            exact
            path={`${BASE_URLS.BUILDINGS}/:buildingId?/:contents?/:contentId?/:action?`}
          >
            <MainLayout>
              <BuildingPage />
            </MainLayout>
          </PrivateRoute>

          <Route exact path="/NotFound" component={NotFoundPage} />
          <Route path="*" component={NotFoundPage} />
        </Switch>
      </Router>
    </QueryClientProvider>
  );
};
