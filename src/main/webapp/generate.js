const fetchGeneratedImages = async (version = "dall-e-2", endpoint = 'mocked', data = {}) => {
    const response = await fetch('resources/v1/' + endpoint + '/generate/' + version, {
        method: "POST",
        mode: "same-origin",
        cache: "no-cache",
        credentials: "same-origin",
        headers: {
            "Content-Type": "application/json"
        },
        redirect: "follow",
        referrerPolicy: "same-origin",
        body: JSON.stringify(data)
    });
    const generations = await response.json();
    const generationsEvent = new CustomEvent('generations-event', {
        detail: {
            payload: generations
        },
        bubbles: true
    });
    window.dispatchEvent(generationsEvent);
    return generations;
};

export { fetchGeneratedImages };