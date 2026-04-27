import React, { useCallback, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  WorkerProfileBackButton,
  WorkerProfileError,
  WorkerProfilePanel,
  WorkerProfileSkeleton,
} from '../../components/ui/WorkerProfilePanel';
import { getCurrentUser } from '../../services/authService';
import { getProfileById } from '../../services/profileService';
import { getReviewsForWorker } from '../../services/reviewService';

const PublicWorkerProfilePage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [profile, setProfile] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchProfile = useCallback(async () => {
    try {
      setLoading(true);
      const data = await getProfileById(id);
      setProfile(data);
      // Fetch reviews using the worker's user ID returned by the profile
      if (data?.userId) {
        try {
          const reviewData = await getReviewsForWorker(data.userId);
          setReviews(Array.isArray(reviewData) ? reviewData : []);
        } catch {
          setReviews([]);
        }
      }
    } catch (err) {
      setError('Failed to fetch profile. It might not exist.');
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    fetchProfile();
  }, [fetchProfile]);

  const handleBack = () => {
    if (window.history.length > 1) {
      navigate(-1);
      return;
    }
    navigate('/browse-workers');
  };

  const handleBookWorker = () => {
    const user = getCurrentUser();
    if (!user) {
      navigate('/login', { state: { from: { pathname: `/book-worker/${id}` } } });
      return;
    }
    if (user.role === 'SEEKER') {
      navigate(`/book-worker/${id}`);
    }
  };

  if (loading) return <WorkerProfileSkeleton />;

  if (error) {
    return (
      <WorkerProfileError
        message={error}
        action={<WorkerProfileBackButton to="/browse-workers" label="Back to Workers" />}
      />
    );
  }

  if (!profile) return null;

  const user = getCurrentUser();
  const showBookCta = !user || user.role === 'SEEKER';

  return (
    <WorkerProfilePanel
      profile={profile}
      reviews={reviews}
      backLink={<button onClick={handleBack} className="ui-link text-white" type="button"><span className="material-icons text-base">arrow_back</span>Back</button>}
      actions={(
        <>
          {showBookCta ? (
            <button className="ui-button-primary" type="button" onClick={handleBookWorker}>
              Book worker
            </button>
          ) : null}
          <button className="ui-button-secondary" onClick={handleBack} type="button">Back</button>
        </>
      )}
    />
  );
};

export default PublicWorkerProfilePage;
