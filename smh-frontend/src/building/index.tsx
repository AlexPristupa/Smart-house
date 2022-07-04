import { useMemo } from 'react';
import { MainLayout } from 'ui';
import { usePageInfo } from './utils';
import { PAGE_CONTENT } from './views';

export const Building: React.FC = () => {
  const pageType = usePageInfo();

  const Content = useMemo(() => PAGE_CONTENT[pageType], [pageType]);

  return <Content />;
};

export const BuildingPage: React.FC = () => (
  <MainLayout>
    <Building />
  </MainLayout>
);
