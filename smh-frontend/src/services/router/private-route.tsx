import { Route, Redirect, RouteProps } from 'react-router-dom';
import { BASE_URLS } from '../constants';

interface IPrivateRouteProps extends RouteProps {
  isAuthenticated: boolean;
}

export const PrivateRoute: React.FC<IPrivateRouteProps> = ({ children, component, isAuthenticated, ...restProps }) => {
  return (
    <Route
      {...restProps}
      render={({ location }) =>
        isAuthenticated ? (
          children
        ) : (
          <Redirect
            to={{
              pathname: `${BASE_URLS.AUTH}/Login`,
              state: { from: location },
            }}
          />
        )
      }
    />
  );
};
