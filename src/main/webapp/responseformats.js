const fetchResponseFormats = async _ => {
    const response = await fetch('resources/v1/response-formats');
    const responseFormats = await response.json();
    const responseFormatsEvent = new CustomEvent('response-formats-event', {
        detail: {
            payload: responseFormats
        },
        bubbles: true
    });
    window.dispatchEvent(responseFormatsEvent);
    return responseFormats;
};

export { fetchResponseFormats };