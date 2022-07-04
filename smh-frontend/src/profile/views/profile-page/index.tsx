import { useRouter } from 'building/utils';
import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import { CardProfileXs } from './card-profile-xs';
import { CardProfileMd } from './card-profile-md';
import { Header } from 'building/components';
import { PageContainer, HeaderButtonsEditDelete, IHeaderButtonsEditDeleteProps, IMenuItemProps, MenuList } from 'ui';
import { useTranslation } from 'react-i18next';
import { EditOutlined, EllipsisOutlined } from '@ant-design/icons';
import { BASE_URLS, useGetProfileData } from 'services';
import { useHistory } from 'react-router';

export const ProfilePage: React.FC = () => {
  const { t } = useTranslation();

  const { push } = useHistory();

  const { history } = useRouter();

  const { md } = useBreakpoint();

  const { data, isLoading, error } = useGetProfileData();

  const propsEditDelete: IHeaderButtonsEditDeleteProps = {
    isLoading,
    editButtonProps: {
      type: 'primary',
      children: t('button.edit'),
      onClick: () => {
        push(`${BASE_URLS.PROFILE}/edit`);
      },
    },
  };

  const getMenuOptions = (): IMenuItemProps[] => [
    {
      label: t('button.edit'),
      icon: <EditOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();
        push(`${BASE_URLS.PROFILE}/edit`);
      },
    },
  ];

  const isAvalible = Boolean(data) || error?.code !== 404;

  return (
    <>
      {md ? (
        <Header onBack={history.goBack} extra={[<HeaderButtonsEditDelete {...propsEditDelete} />]} />
      ) : (
        <Header onBack={history.goBack} extra={[<MenuList children={<EllipsisOutlined />} options={getMenuOptions()} />]} />
      )}
      <PageContainer avalibility={isAvalible} isLoading={isLoading}>
        {!md ? <CardProfileXs data={data!} /> : <CardProfileMd data={data!} />}
      </PageContainer>
    </>
  );
};
