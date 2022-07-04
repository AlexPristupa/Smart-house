import { Avatar, List } from 'antd';
import { getFileSrc, getFio, IFileContent, IProfile, parseTime } from 'services';
import { CardHardwareImage } from 'ui';
import { useTranslation } from 'react-i18next';
import { UserOutlined } from '@ant-design/icons';
import React from 'react';
import styled from 'styled-components';

interface ICardProfileXsProps {
  data: IProfile;
}
const ImageWrapper = styled.div`
  margin-bottom: 10px;
`;

export const CardProfileXs: React.FC<ICardProfileXsProps> = ({ data }) => {
  const { t } = useTranslation();

  return (
    <>
      <List
        itemLayout="vertical"
        dataSource={[data]}
        renderItem={({ lastName, firstName, patronymic, email, birthDate, photo }) => (
          <List.Item>
            <ImageWrapper>
              {photo ? (
                <CardHardwareImage image={photo && photo && !Array.isArray(photo) ? getFileSrc(photo as IFileContent, true) : undefined} />
              ) : (
                <Avatar size={150} style={{ borderRadius: '40px' }} icon={!photo && <UserOutlined />} />
              )}
            </ImageWrapper>
            <List.Item.Meta
              title={t('profile.fio')}
              description={getFio({ lastName, firstName, patronymic })}
              style={{ overflowWrap: 'break-word' }}
            />
            <List.Item.Meta title={t('profile.email')} description={email} />
            {birthDate && <List.Item.Meta title={t('profile.birthDate')} description={parseTime(birthDate, '{d}.{m}.{y}')} />}
          </List.Item>
        )}
      />
    </>
  );
};
