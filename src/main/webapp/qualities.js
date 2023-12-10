const fetchQualities = async model => {
    const response = await fetch('resources/v1/qualities/' + model);
    const qualities = await response.json();
    const qualitiesEvent = new CustomEvent('qualities-event', {
        detail: {
            payload: qualities
        },
        bubbles: true
    });
    window.dispatchEvent(qualitiesEvent);
    return qualities;
};

export { fetchQualities };