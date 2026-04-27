import React, { useCallback, useEffect, useState } from 'react';
import { Link, Navigate, useNavigate, useParams } from 'react-router-dom';
import { getCurrentUser } from '../../services/authService';
import { getProfileById } from '../../services/profileService';
import { createBooking } from '../../services/bookingService';
import { PageIntro, LoadingPanel } from '../../components/ui/PortalPrimitives';
import ErrorBanner from '../../components/common/ErrorBanner';
import { useToast } from '../../components/common/ToastContext';

const BookWorkerPage = () => {
  const { profileId } = useParams();
  const navigate = useNavigate();
  const toast = useToast();
  const user = getCurrentUser();
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [loadError, setLoadError] = useState('');
  const [formError, setFormError] = useState('');

  const [bookingDate, setBookingDate] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [note, setNote] = useState('');

  const load = useCallback(async () => {
    if (!profileId) return;
    setLoading(true);
    setLoadError('');
    try {
      const data = await getProfileById(profileId);
      setProfile(data);
    } catch {
      setLoadError('Could not load this worker profile.');
    } finally {
      setLoading(false);
    }
  }, [profileId]);

  useEffect(() => {
    load();
  }, [load]);

  if (user && user.role !== 'SEEKER') {
    return <Navigate to={user.role === 'WORKER' ? '/worker/dashboard' : '/'} replace />;
  }

  if (!user) {
    return <Navigate to="/login" state={{ from: { pathname: `/book-worker/${profileId}` } }} replace />;
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFormError('');
    if (!bookingDate || !startTime || !endTime) {
      setFormError('Please choose a date, start time, and end time.');
      return;
    }
    if (startTime >= endTime) {
      setFormError('End time must be after start time.');
      return;
    }
    setSaving(true);
    try {
      await createBooking({
        workerProfileId: Number(profileId),
        bookingDate,
        startTime: startTime.length === 5 ? `${startTime}:00` : startTime,
        endTime: endTime.length === 5 ? `${endTime}:00` : endTime,
        note: note.trim() || null,
      });
      toast.success('Booking request sent. The worker will confirm it from their schedule.');
      navigate('/my-bookings', { replace: true });
    } catch (err) {
      setFormError(
        err.response?.data?.message || 'Could not create the booking. Please try again.',
      );
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div className="page-wrapper">
        <main className="ui-shell">
          <LoadingPanel message="Loading profile…" />
        </main>
      </div>
    );
  }

  if (loadError || !profile) {
    return (
      <div className="page-wrapper">
        <main className="ui-shell max-w-2xl">
          <p className="text-white/90">{loadError || 'Profile not found.'}</p>
          <Link to="/browse-workers" className="ui-link mt-4 inline-flex text-white">Back to workers</Link>
        </main>
      </div>
    );
  }

  const workerLabel = profile.fullName || 'This worker';

  return (
    <div className="page-wrapper">
      <main className="ui-shell max-w-2xl space-y-6">
        <PageIntro
          eyebrow="Book a slot"
          title={`Book ${workerLabel}`}
          subtitle="Choose a day and time window. You do not need to describe the job here. The worker will confirm or decline based on their availability. You will see their phone number on this booking after they accept."
          light
        />

        <div className="ui-panel p-5 sm:p-6">
          <h2 className="text-lg font-bold text-ink">Basic details</h2>
          <p className="mt-1 text-sm text-ink-muted">
            We only need a time window. Contact numbers are shared after the worker accepts.
          </p>
          <form onSubmit={handleSubmit} className="mt-6 space-y-5" noValidate>
            <ErrorBanner message={formError} />

            <div className="ui-field">
              <label className="ui-label" htmlFor="bookingDate">Date</label>
              <input
                id="bookingDate"
                type="date"
                className="ui-input"
                value={bookingDate}
                onChange={(e) => setBookingDate(e.target.value)}
                min={new Date().toISOString().slice(0, 10)}
                required
              />
            </div>

            <div className="grid gap-4 sm:grid-cols-2">
              <div className="ui-field">
                <label className="ui-label" htmlFor="startTime">From</label>
                <input
                  id="startTime"
                  type="time"
                  className="ui-input"
                  value={startTime}
                  onChange={(e) => setStartTime(e.target.value)}
                  required
                />
              </div>
              <div className="ui-field">
                <label className="ui-label" htmlFor="endTime">To</label>
                <input
                  id="endTime"
                  type="time"
                  className="ui-input"
                  value={endTime}
                  onChange={(e) => setEndTime(e.target.value)}
                  required
                />
              </div>
            </div>

            <div className="ui-field">
              <label className="ui-label" htmlFor="note">Note to worker (optional)</label>
              <textarea
                id="note"
                className="ui-textarea"
                rows={3}
                value={note}
                onChange={(e) => setNote(e.target.value)}
                maxLength={500}
                placeholder="E.g. preferred entrance, rough timing, or a one-line context — if you like."
              />
              <p className="ui-helper">Max 500 characters. No job description is required.</p>
            </div>

            <div className="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
              <Link to={`/workers/${profileId}`} className="ui-link order-2 sm:order-1">Back to profile</Link>
              <button type="submit" className="ui-button-primary order-1 sm:order-2" disabled={saving}>
                {saving ? 'Sending…' : 'Send booking request'}
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default BookWorkerPage;
