from google.appengine.ext import ndb

class KeyWord(ndb.Model):
	keyWord = ndb.StringProperty()
	count = ndb.IntegerProperty()

class Tag(ndb.Model):
	tag = ndb.StringProperty()
	probability = ndb.FloatProperty()

class Image(ndb.Model):
	blobKey = ndb.StringProperty()
	tags = ndb.StructuredProperty(Tag, repeated=True)
	keyWords = ndb.StructuredProperty(KeyWord, repeated=True)
	link = ndb.StringProperty()
	siteLink = ndb.StringProperty()
	flickrTags = ndb.StringProperty(repeated=True)

class User(ndb.Model):
	phoneNumber = ndb.StringProperty()
	regID = ndb.StringProperty()
	keyWords = ndb.StructuredProperty(KeyWord, repeated=True)

class ImageUsed(ndb.Model):
	image = ndb.KeyProperty(kind=Image)
	user = ndb.KeyProperty(kind=User)
	count = ndb.IntegerProperty()

class RelatedUsers(ndb.Model):
	user1 = ndb.KeyProperty(kind=User)
	user2 = ndb.KeyProperty(kind=User)
	relation = ndb.IntegerProperty()

	def get_related_user(self, user):
		if self.user1 == user:
			return self.user2
		else:
			return self.user1







