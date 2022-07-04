import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import { CardServiceMd } from './card-services-md';
import { CardServicesXs } from './card-services-xs';
import { useRouter } from 'building/utils';
import { Header } from 'building/components';
import { useDeleteService, useGetServiceData } from 'building/hooks';
import { HeaderButtonsEditDelete, IHeaderButtonsEditDeleteProps, IMenuItemProps, MenuList, PageContainer } from 'ui';
import { Modal } from 'antd';
import { BUTTON_SYSTEM, IListData, useIsRole } from 'services';
import { useQueryClient } from 'react-query';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { IService } from 'building/interfaces';
import { useTranslation } from 'react-i18next';
import { DeleteOutlined, EditOutlined, EllipsisOutlined, ExclamationCircleOutlined } from '@ant-design/icons';

export const CardService: React.FC = () => {
  const { goToBuildingPage, goToEditPage } = useRouter();
  const queryClient = useQueryClient();
  const { isNotReader } = useIsRole();
  const { t } = useTranslation();

  const {
    params: { buildingId, contentId },
  } = useRouter();

  const { data, isLoading, error } = useGetServiceData(contentId!);

  const { mutate: deleteService } = useDeleteService({
    onMutate: async val => {
      await queryClient.cancelQueries(BUILDING_QUERY_KEYS.SERVICE_LIST);
      await queryClient.cancelQueries([BUILDING_QUERY_KEYS.SERVICE, val]);

      const previousService = queryClient.getQueryData([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId]) as IListData<IService>;

      const newData = previousService ? previousService.content.filter(item => item.id !== val) : [];

      queryClient.setQueryData<Array<IService>>([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId], () => [...newData]);

      return { previousService };
    },
    onSuccess: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.SERVICE_LIST);
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE, contentId]);
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.BUILDING, buildingId]);

      goToBuildingPage();
    },
    onError: (error, variables, context: any) => {
      queryClient.setQueryData(BUILDING_QUERY_KEYS.SERVICE_LIST, context.previousHardware);
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE, contentId]);
    },
    onSettled: (error, context) => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.SERVICE_LIST);
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE, contentId]);

      goToBuildingPage();
    },
  });

  const { md } = useBreakpoint();

  console.log('queryClient rrrr', queryClient);

  const propsEditDelete: IHeaderButtonsEditDeleteProps = {
    isLoading: false,
    editButtonProps: {
      type: 'primary',
      children: BUTTON_SYSTEM.EDIT,
      onClick: () => {
        goToEditPage(contentId!);
      },
    },
    deleteButtonProps: {
      children: BUTTON_SYSTEM.DELETE,
      onClick: () => {
        Modal.confirm({
          title: t('messageSystem.modal.deleteTitle'),
          okText: t('button.delete'),
          cancelText: t('button.cancel'),
          icon: <ExclamationCircleOutlined />,
          onOk: () => {
            if (contentId) deleteService(parseInt(contentId));
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
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
        goToEditPage(contentId!);
      },
    },
    {
      label: t('button.delete'),
      icon: <DeleteOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();

        Modal.confirm({
          title: t('messageSystem.modal.deleteTitle'),
          okText: t('button.delete'),
          cancelText: t('button.cancel'),
          icon: <ExclamationCircleOutlined />,
          onOk: () => {
            if (contentId) deleteService(parseInt(contentId));
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
      },
      danger: true,
    },
  ];

  const isAvalible = Boolean(data) || error?.code !== 404;

  return (
    <>
      {md ? (
        <Header
          onBack={() => {
            goToBuildingPage();
          }}
          extra={isNotReader() ? [<HeaderButtonsEditDelete {...propsEditDelete} />] : <></>}
        />
      ) : (
        <Header
          onBack={goToBuildingPage}
          extra={isNotReader() ? [<MenuList children={<EllipsisOutlined />} options={getMenuOptions()} />] : <></>}
        />
      )}
      <PageContainer avalibility={isAvalible} isLoading={isLoading}>
        {!md ? <CardServicesXs data={data!} /> : <CardServiceMd data={data!} />}
      </PageContainer>
    </>
  );
};
