import { Redirect } from 'react-router-dom';
import { LoaderOverlay } from 'ui/loaders';

interface IPageContainerProps {
  avalibility: boolean;
  isLoading?: boolean;
  fallback?: React.ReactNode;
}

/**
 * Компонент, оборачивающий страницу и проверяющий на ее доступность.
 *
 * Если не доступен, то переводит на страницу 404 или fallback указаный.
 */
export const PageContainer: React.FC<IPageContainerProps> = ({ avalibility, isLoading, fallback, children }) => {
  if (isLoading) {
    return <LoaderOverlay />;
  }

  if (avalibility) {
    return <div>{children}</div>;
  }

  return <div>{fallback}</div> || <Redirect to="/NotFound" />;
};
