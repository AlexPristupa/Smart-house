import { useHistory } from 'react-router-dom';
import { BASE_URLS } from 'services';

export const useRouter = () => {
  const history = useHistory();

  const goToBuildingsList = () => history.push(BASE_URLS.BUILDINGS);

  const goToManual = () => history.push(BASE_URLS.MANUAL);

  const goToUserList = () => history.push(BASE_URLS.USERS);

  const changeHeaderTab = (headerTab: string) => history.push(`${headerTab}`);

  return {
    history,
    goToManual,
    goToUserList,
    goToBuildingsList,
    changeHeaderTab,
  };
};
