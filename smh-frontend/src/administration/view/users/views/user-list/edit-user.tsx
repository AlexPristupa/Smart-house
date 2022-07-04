import { useCallback } from 'react';
import { Form } from 'antd';
import { useQueryClient } from 'react-query';
import { useUsersRouter } from 'administration/view/users/utils';
import { useEditUser, useGetUserData } from 'administration/view/users/hooks';
import { IUser, IUserObject } from 'administration/view/users/interfaces';
import { UserForm } from 'administration/view/users/component/form';
import { ADMIN_QUERY_KEYS } from 'administration/constants';
import { errorHandler } from 'services';

export const EditUser: React.FC = () => {
  const queryClient = useQueryClient();
  const {
    params: { userId },
    goToUserList,
    goToUserPage,
  } = useUsersRouter();

  const { data, isLoading: isUserLoading } = useGetUserData(parseInt(userId!));

  const [form] = Form.useForm<IUser>();

  const invalidateQueries = () => {
    queryClient.invalidateQueries(ADMIN_QUERY_KEYS.USER_LIST);
    queryClient.invalidateQueries([ADMIN_QUERY_KEYS.USER, userId]);
  };

  const { mutate, isLoading: isSubmitting } = useEditUser(parseInt(userId!), {
    onMutate: async () => {
      await queryClient.cancelQueries(ADMIN_QUERY_KEYS.USER_LIST);
      await queryClient.cancelQueries([ADMIN_QUERY_KEYS.USER, userId]);
    },
    onSuccess: () => {
      invalidateQueries();
      form.resetFields();
      goToUserPage(parseInt(userId!));
    },
    onError: error => {
      errorHandler(error, form);
    },
  });

  const onCancel = useCallback(() => {
    goToUserList();
  }, [goToUserList]);

  const onFinish = useCallback(
    (values: IUserObject) => {
      const usersObject = {
        ...values,
        id: parseInt(userId!),
      };
      mutate(usersObject);
    },
    [userId, mutate],
  );

  const isLoading = isUserLoading || isSubmitting;

  if (data) {
    return <UserForm form={form} isLoading={isLoading} initialValues={data} onCancel={onCancel} onFinish={onFinish} />;
  }

  return null;
};
