import { Avatar, List } from 'antd';
import React from 'react';
import { getFileSrc, getFio, IFileContent } from 'services';
import { useTranslation } from 'react-i18next';
import { CardHardwareImage } from 'ui';
import { UserOutlined } from '@ant-design/icons';
import styled from 'styled-components';
import { IUser } from 'administration/view/users/interfaces';

interface IProps {
  data: IUser;
}
const ImageWrapper = styled.div`
  margin-bottom: 10px;
`;

export const CardServicesXs: React.FC<IProps> = ({ data }) => {
  const { t } = useTranslation();

  if (data) {
    return (
      <List
        itemLayout="vertical"
        dataSource={[data!]}
        renderItem={({ lastName, firstName, patronymic, email, photo, role }) => (
          <List.Item>
            <ImageWrapper>
              {photo ? (
                <CardHardwareImage image={photo && photo && !Array.isArray(photo) ? getFileSrc(photo as IFileContent, true) : undefined} />
              ) : (
                <div>
                  <Avatar size={100} style={{ borderRadius: '15px' }} icon={!data.photo && <UserOutlined />} />
                </div>
              )}
            </ImageWrapper>
            <List.Item.Meta
              title={t('profile.fio')}
              description={getFio({ lastName, firstName, patronymic })}
              style={{ overflowWrap: 'break-word' }}
            />
            <List.Item.Meta title={t('users.card.email')} description={email} />
            <List.Item.Meta title={t('users.card.role')} description={t<any>(`users.roles.${role}`)} />
          </List.Item>
        )}
      />
    );
  }

  return null;
};
