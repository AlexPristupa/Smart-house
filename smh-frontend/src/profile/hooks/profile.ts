import { useMutation, UseMutationOptions } from 'react-query';
import { appendUploadFilesToFormData, filterUploadFilesFromEntity, formDataRequest, IProfile, IServerError } from 'services';

export const useEditProfile = (options?: UseMutationOptions<IProfile, IServerError, IProfile>) =>
  useMutation<IProfile, IServerError, IProfile>(profileData => {
    const formData = new FormData();

    appendUploadFilesToFormData(formData, 'photo', profileData.photo);

    formData.append('profile', new Blob([JSON.stringify(filterUploadFilesFromEntity(profileData))], { type: 'application/json' }));

    return formDataRequest({ url: 'profile', data: formData, method: 'POST' });
  }, options);
