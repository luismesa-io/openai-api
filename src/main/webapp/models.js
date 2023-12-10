const fetchModels = async _ => {
    const response = await fetch('resources/v1/models');
    const models = await response.json();
    const modelsEvent = new CustomEvent('models-event', {
        detail: {
            payload: models
        },
        bubbles: true
    });
    window.dispatchEvent(modelsEvent);
    return models;
};

const fetchMaxN = model => {
    if (model === 'dall-e-2')
        return 10;
    return 1;
};

export { fetchModels, fetchMaxN };