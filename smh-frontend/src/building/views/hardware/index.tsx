import { useRouter } from 'building/utils';
import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import { CardHardwareXs } from './card-hardware-xs';
import { CardHardwareMd } from './card-hardware-md';
import { Header } from 'building/components';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useQueryClient } from 'react-query';
import { useDeleteHardware, useGetHardwareData } from 'building/hooks';
import { PageContainer, HeaderButtonsEditDelete, IHeaderButtonsEditDeleteProps, IMenuItemProps, MenuList } from 'ui';
import { Modal } from 'antd';
import { useTranslation } from 'react-i18next';
import { DeleteOutlined, EditOutlined, EllipsisOutlined, ExclamationCircleOutlined } from '@ant-design/icons';
import { useIsRole } from 'services';

export const CardHardware: React.FC = () => {
  const { t } = useTranslation();
  const {
    params: { contentId },
  } = useRouter();

  const { isNotReader } = useIsRole();

  const queryClient = useQueryClient();

  const { goToEditPage, goToBuildingPage } = useRouter();

  const { md } = useBreakpoint();

  const { data, isLoading, error } = useGetHardwareData(contentId!);

  const { mutate: deleteHardware } = useDeleteHardware({
    onMutate: async val => {
      await queryClient.cancelQueries(BUILDING_QUERY_KEYS.HARDWARE_LIST);
      await queryClient.cancelQueries([BUILDING_QUERY_KEYS.HARDWARE, val]);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.HARDWARE_LIST);
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.HARDWARE, contentId]);

      goToBuildingPage();
    },
    onSettled: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.HARDWARE_LIST);
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.HARDWARE, contentId]);

      goToBuildingPage();
    },
  });

  const propsEditDelete: IHeaderButtonsEditDeleteProps = {
    isLoading,
    editButtonProps: {
      type: 'primary',
      children: t('button.edit'),
      onClick: () => {
        goToEditPage(contentId!);
      },
    },

    deleteButtonProps: {
      children: t('button.delete'),
      onClick: () => {
        Modal.confirm({
          title: t('messageSystem.modal.deleteTitle'),
          okText: t('button.delete'),
          cancelText: t('button.cancel'),
          icon: <ExclamationCircleOutlined />,
          onOk: () => {
            if (contentId) deleteHardware(contentId);
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
            if (contentId) deleteHardware(contentId);
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
        <Header onBack={goToBuildingPage} extra={isNotReader() ? [<HeaderButtonsEditDelete {...propsEditDelete} />] : <></>} />
      ) : (
        <Header
          onBack={goToBuildingPage}
          extra={isNotReader() ? [<MenuList children={<EllipsisOutlined />} options={getMenuOptions()} />] : <></>}
        />
      )}
      <PageContainer avalibility={isAvalible} isLoading={isLoading}>
        {!md ? <CardHardwareXs /> : <CardHardwareMd data={data!} />}
      </PageContainer>
    </>
  );
};
