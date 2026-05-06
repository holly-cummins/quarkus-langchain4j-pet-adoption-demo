import {useEffect, useState} from 'react';
import './Pet.css'

function toImage(animal) {
    return `/quinoa/${animal.toLowerCase()}.png`;
}

export default function Pet({ preference }) {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchData = async () => {
        setLoading(true);
        try {
            const url = preference
                ? `/api/pet?preference=${encodeURIComponent(preference)}`
                : `/api/pet`;
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`HTTP error: Status ${response.status}`);
            }
            let postsData = await response.json();
            setData(postsData);
            setError(null);
        } catch (err) {
            setError(err.message);
            setData(null);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (!loading) {
            fetchData();
        }
    }, [preference]);

    return data && <div className="card">
            <div className="colour" style={{background: data.hexColour}}/>
            <h2>{data.name} the {data.type}</h2>
            <div>
                <div className="row">
                    <img className="portrait" src={toImage(data.animal)} alt={data.animal}/>
                    <div className="details">
                        <div className="detail-heading">Colour</div>
                        <div>{data.colour}</div>
                        <div className="detail-heading">Diet</div>
                        <div>{data.diet}
                            <div></div>
                        </div>
                    </div>
                </div>
                <div className="description">{data.description}</div>
            </div>

        </div> ||
        <div className="card blank">
            <div>Invoking the magic gerbils ...</div>
            <img className="spinner" alt="..." src="/quinoa/spinner.png"></img>
        </div>;
};

