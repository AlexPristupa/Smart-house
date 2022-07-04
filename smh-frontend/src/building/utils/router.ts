import { IBuildingUrlParams, PAGE_ACTIONS } from 'building/interfaces';
import { BUILDNG_CONTENTS } from 'building/constants';
import { useParams, useHistory } from 'react-router-dom';
import { BASE_URLS, goToLink } from 'services';

export const useGoToBuildingPage = () => {
  const { buildingId, contents } = useParams<IBuildingUrlParams>();

  return goToLink(`/Buildings/${buildingId}/${contents}`);
};

export const useRouter = () => {
  const params = useParams<IBuildingUrlParams>();
  const history = useHistory();
  const { buildingId, contents: baseContents } = params;

  const contents = baseContents || BUILDNG_CONTENTS.SERVICE;

  const goToBuilding = () => history.push(`${BASE_URLS.BUILDINGS}/${buildingId}`);
  const goToBuildingPage = () => history.push(`${BASE_URLS.BUILDINGS}/${buildingId}/${contents}`);

  const goToBuildingsList = () => history.push(`${BASE_URLS.BUILDINGS}`);

  const changeBuildingTab = (key: string) => history.push(`${BASE_URLS.BUILDINGS}/${buildingId}/${key}`);

  const goToAddBuilding = () => history.push(`${BASE_URLS.BUILDINGS}/new`);

  const goToEditBuilding = (id?: number) => history.push(`${BASE_URLS.BUILDINGS}/${id || buildingId}/edit`);

  const goToContentPage = (id: number, buildingIdIn: string | undefined = undefined, contentsIn: string | undefined = undefined) => {
    if (buildingIdIn && contentsIn) {
      history.push(`${BASE_URLS.BUILDINGS}/${buildingIdIn}/${contentsIn}/${id}`);
    } else {
      history.push(`${BASE_URLS.BUILDINGS}/${buildingId}/${contents}/${id}`);
    }
  };

  const goToEditPage = (id: string) => history.push(`${BASE_URLS.BUILDINGS}/${buildingId}/${contents}/${id}/edit`);

  const goToInsertPage = () => history.push(`${BASE_URLS.BUILDINGS}/${buildingId}/${contents}/${PAGE_ACTIONS.NEW}`);

  const goToNotifications = () => history.push(`${BASE_URLS.NOTIFICATIONS}`);

  return {
    params,
    history,
    goToBuildingPage,
    goToBuildingsList,
    changeBuildingTab,
    goToEditPage,
    goToContentPage,
    goToInsertPage,
    goToAddBuilding,
    goToEditBuilding,
    goToBuilding,
    goToNotifications,
  };
};
