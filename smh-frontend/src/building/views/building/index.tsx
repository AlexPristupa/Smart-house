import React, { useCallback, Suspense } from 'react';
import { Card, Modal, Tabs, Typography } from 'antd';
import { BUILDING_QUERY_KEYS, BUILDNG_CONTENTS } from 'building/constants';
import { IBuilding } from 'building/interfaces';
import { useQueryClient } from 'react-query';
import { getFileSrc, IFileContent, IListData, optimisticUpdatesDelete, useIsRole } from 'services';
import styled from 'styled-components';
import { CardHardwareImage, PageContainer, HeaderButtonsEditDelete, IHeaderButtonsEditDeleteProps, MenuList, IMenuItemProps } from 'ui';
import { HardwareTab, ServicesTab, DocumentationsTab } from './tabs';
import { Header, TabActionsButton } from 'building/components';
import { useRouter } from 'building/utils';
import { useDeleteBuilding, useGetBuildingData } from 'building/hooks';
import { useTranslation } from 'react-i18next';
import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import { DeleteOutlined, EditOutlined, EllipsisOutlined, ExclamationCircleOutlined } from '@ant-design/icons';

const { TabPane } = Tabs;

interface IBuildingImageProps {
  md: boolean;
}

const BuildingImage = styled.div<IBuildingImageProps>`
  width: 100%;
  background-position: center;
  background-size: cover;
  display: flex;
  flex-direction: ${props => (props.md ? 'row' : 'column')};
  justify-content: start;
  align-items: center;
  margin-bottom: 20px;
`;

const Title = styled.div`
  margin-left: 55px;
`;
const BuildingTabs = styled(Tabs)`
  & .ant-tabs-nav-wrap {
    margin: 0px 16px;
  }
`;

const TitleText = styled(Typography.Text)`
  margin-top: 0;
  font-size: 16px;
`;

const DivXsTabActionsButton = styled.div`
  display: flex;
  justify-content: center;
  position: sticky;
  background-color: rgba(239, 242, 244, 1);
  bottom: 0;
  padding: 20px;
  width: 100%;
  & span {
    width: 100%;
    & .ant-upload.ant-upload-select {
      width: 100%;
    }
  }
`;

export const Building: React.FC = () => {
  const { t } = useTranslation();
  const { isNotReader } = useIsRole();

  const queryClient = useQueryClient();
  const {
    params: { contents, buildingId },
    changeBuildingTab,
    goToBuildingsList,
    goToEditBuilding,
  } = useRouter();

  const { data, error, isLoading } = useGetBuildingData(buildingId!);

  const handleChangeTab = useCallback((key: string) => changeBuildingTab(key), [changeBuildingTab]);

  const isAvalible = Boolean(data) || error?.[0].code !== 404;

  const { mutate } = useDeleteBuilding({
    onMutate: async val => {
      await queryClient.cancelQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);

      const previousBuilding = optimisticUpdatesDelete(val, BUILDING_QUERY_KEYS.BUILDING_LIST, queryClient);

      return { previousBuilding };
    },
    onSuccess: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);
      goToBuildingsList();
    },
    onError: (error, variables, context: any) => {
      if (context?.previousBuilding) {
        const previousData = context?.previousBuilding as unknown as IListData<IBuilding>;
        queryClient.setQueryData(BUILDING_QUERY_KEYS.BUILDING_LIST, previousData);
      }
    },
    onSettled: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);

      goToBuildingsList();
    },
  });

  const propsEditDelete: IHeaderButtonsEditDeleteProps = {
    isLoading,
    editButtonProps: {
      type: 'primary',
      children: t('button.edit'),
      onClick: () => {
        goToEditBuilding();
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
            if (buildingId) mutate(buildingId);
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
        goToEditBuilding();
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
            if (buildingId) mutate(buildingId);
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
      },
      danger: true,
    },
  ];

  const { md } = useBreakpoint();

  return (
    <Suspense fallback="Loading...">
      {md ? (
        <Header onBack={goToBuildingsList} extra={isNotReader() ? [<HeaderButtonsEditDelete {...propsEditDelete} />] : <></>} />
      ) : (
        <Header
          onBack={goToBuildingsList}
          extra={isNotReader() ? [<MenuList children={<EllipsisOutlined />} options={getMenuOptions()} />] : <></>}
        />
      )}
      <PageContainer avalibility={isAvalible} isLoading={isLoading}>
        <Card bordered={false}>
          <BuildingImage md={md!}>
            <CardHardwareImage
              image={data && data.photo && !Array.isArray(data.photo) ? getFileSrc(data.photo as IFileContent, true) : undefined}
            />
            <Title style={{ wordBreak: 'break-word' }}>
              <Typography.Title level={2}>{data?.name}</Typography.Title>
              <TitleText>{data?.address}</TitleText>
            </Title>
          </BuildingImage>

          <BuildingTabs
            defaultActiveKey={BUILDNG_CONTENTS.SERVICE}
            activeKey={contents}
            onTabClick={handleChangeTab}
            tabBarExtraContent={isNotReader() && md && <TabActionsButton />}
          >
            <TabPane tab={t('tabName.services')} key={BUILDNG_CONTENTS.SERVICE}>
              <ServicesTab />
            </TabPane>
            <TabPane tab={t('tabName.hardware')} key={BUILDNG_CONTENTS.HARDWARE}>
              <HardwareTab />
            </TabPane>
            <TabPane tab={t('tabName.documentation')} key={BUILDNG_CONTENTS.DOCUMENTATION}>
              <DocumentationsTab />
            </TabPane>
          </BuildingTabs>
        </Card>
      </PageContainer>
      {isNotReader() && !md && (
        <DivXsTabActionsButton>
          <TabActionsButton />
        </DivXsTabActionsButton>
      )}
    </Suspense>
  );
};
