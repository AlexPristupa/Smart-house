import { ButtonProps, List } from 'antd';
import { AddButton, AddButtonBottom } from 'building/components';
import { Header } from 'building/components';
import { useTranslation } from 'react-i18next';
import { useState } from 'react';
import { AddServiceType } from './add-service-type';
import { TypesListItem } from './list-item';
import { useGetServiceTypes } from '../hooks';
import useBreakpoint from 'antd/es/grid/hooks/useBreakpoint';

export const TypeList: React.FC = () => {
  const { data, isLoading } = useGetServiceTypes();
  const { t } = useTranslation();
  const { md } = useBreakpoint();

  const [visible, setVisible] = useState(false);

  const addButtonProps: ButtonProps = { children: t('typeList.addType'), onClick: () => setVisible(true) };

  const onCancel = () => {
    setVisible(false);
  };

  return (
    <>
      <Header title={t('typeList.title')} extra={[md && <AddButton {...addButtonProps} />]} />
      <List
        bordered={false}
        loading={isLoading}
        itemLayout="horizontal"
        dataSource={data?.content}
        renderItem={item => <TypesListItem item={item} isLoading={isLoading} />}
      />
      <AddServiceType visible={visible} onCancel={onCancel} onOk={onCancel} />
      <AddButtonBottom {...addButtonProps} />
    </>
  );
};
