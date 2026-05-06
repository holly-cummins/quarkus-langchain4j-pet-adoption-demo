import './App.css'
import Pet from './Pet'
import { useState } from 'react'

function App() {
    const [petPreference, setPetPreference] = useState('');
    const [submittedPreference, setSubmittedPreference] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        setSubmittedPreference(petPreference);
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSubmit(e);
        }
    };

    return (
        <>
            <h1 className="title">The Pet Ai-doption Ai-gency</h1>
            <div className="preference-input">
                <label htmlFor="petPreference">What kind of pet are you looking for?</label>
                <input
                    id="petPreference"
                    type="text"
                    value={petPreference}
                    onChange={(e) => setPetPreference(e.target.value)}
                    onKeyPress={handleKeyPress}
                    placeholder="e.g., fluffy and friendly, self-sufficient, something unusual..."
                />
            </div>
            <h2 className="featured-pet-heading">Today's Featured Pet</h2>
            <div className="invitation">This is a wonderful ai-pportunity for you to ai-dopt:</div>
            <Pet preference={submittedPreference}></Pet>

        </>
    )
}

export default App
